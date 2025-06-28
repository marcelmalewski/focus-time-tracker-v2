import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';
import { PageLinks } from '../../other/typesAndConsts';

@Component({
    selector: 'page-not-found',
    templateUrl: './page-not-found.component.html',
    styleUrls: [],
    standalone: true,
    imports: [RouterLink, FormsModule, NgIf, ReactiveFormsModule],
})
export class PageNotFoundComponent {
    protected readonly PageLinks = PageLinks;
}
