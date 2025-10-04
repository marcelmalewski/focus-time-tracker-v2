import {
    Component,
    CUSTOM_ELEMENTS_SCHEMA,
    OnDestroy,
    OnInit,
    ViewChild,
} from '@angular/core';
import { CommandLineComponent } from '../command-line/command-line.component';
import { MatFormField, MatInput } from '@angular/material/input';
import { HttpResponse } from '@angular/common/http';
import { Subject, takeUntil } from 'rxjs';
import { Router } from '@angular/router';
import { PrincipalDataService } from '../../service/principal-data.service';
import { NotificationService } from '../../service/notification.service';
import {
    MatOption,
    MatSelect,
    MatSelectModule,
} from '@angular/material/select';
import { DatePipe, NgIf } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDivider } from '@angular/material/divider';
import { FormsModule, NgForm } from '@angular/forms';
import {
    MatCard,
    MatCardContent,
    MatCardHeader,
    MatCardTitleGroup,
} from '@angular/material/card';
import { MatSlideToggle } from '@angular/material/slide-toggle';
import { TimerService } from '../../service/timer.service';
import { BottomMenuComponent } from '../bottom-menu/bottom-menu.component';
import {
    FocusSessionBasicData,
    MainTopicBasicData,
    TimerSettings,
} from '../../spec/person-spec';
import {
    AtLeastOneMessage,
    LessThanOrEqual99Message,
    AtLeastZeroMessage,
    LessThanOrEqual59Message,
    UnknownServerErrorMessage,
    TimerSettingsUpdated,
    NotImplementedYet,
} from '../../spec/message-spec';
import { Pages, Paginated, Stages } from '../../spec/common-spec';
import { FocusSessionService } from '../../service/focus-session.service';
import { TimerFieldPipe } from '../../pipes/timer-field.pipe';
import { translateBreakPipe } from '../../pipes/translate-break.pipe';
import { translateFinishedPipe } from '../../pipes/translate-finished.pipe';
import { MatPaginator, PageEvent } from '@angular/material/paginator';

@Component({
    selector: 'app-home',
    standalone: true,
    imports: [
        BottomMenuComponent,
        MatCard,
        MatCardHeader,
        MatCardTitleGroup,
        MatCardContent,
        NgIf,
        TimerFieldPipe,
        translateBreakPipe,
        translateFinishedPipe,
        DatePipe,
        MatPaginator,
    ],
    templateUrl: './focus-sessions.component.html',
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class FocusSessionsComponent implements OnDestroy, OnInit {
    pageIndex = 0;
    defaultPageSizes = [10];
    pageSize = 10;

    paginatedFocusSessions: Paginated<FocusSessionBasicData> | undefined;

    readonly Pages = Pages;
    private readonly componentDestroyed$ = new Subject<void>();

    constructor(private focusSessionService: FocusSessionService) {}

    ngOnInit(): void {
        this.focusSessionService
            .getPrincipalAllFocusSessions(this.pageIndex, this.pageSize)
            .pipe(takeUntil(this.componentDestroyed$))
            .subscribe(paginatedResult => {
                this.paginatedFocusSessions = paginatedResult;
            });
    }

    ngOnDestroy(): void {
        this.componentDestroyed$.next();
        this.componentDestroyed$.complete();
    }

    handlePageEvent(event: PageEvent) {
        this.pageIndex = event.pageIndex;
        this.pageSize = event.pageSize;

        this.focusSessionService
            .getPrincipalAllFocusSessions(this.pageIndex, this.pageSize)
            .pipe(takeUntil(this.componentDestroyed$))
            .subscribe(paginatedResult => {
                this.paginatedFocusSessions = paginatedResult;
            });
    }
}
