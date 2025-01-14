import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { PrincipalBasicData } from '../interface/person.interface';

@Injectable({
    providedIn: 'root',
})
export class GeneralActionsService {
    private principalBasicData: PrincipalBasicData | undefined;

    constructor(private http: HttpClient) {}

    logout(): Observable<boolean> {
        return this.http.get<boolean>('/api/v1/persons/principal/logged-in');
    }
}

// w poprzednim projekcie bylo cos takiego ale teraz chyba juz nie zwroci 500
// logout() {
//     //TODO sprawdzic co zwroci błąd po serwerze czy napewno 500 cos
//     // @ts-ignore
//     this.http.post('api/login?logout').subscribe({
//         next: () => {},
//         error: (response: HttpResponse<any>) => {
//             if (response.status === 401) {
//                 this.myDataService.setMyData(undefined);
//                 this.navigationService.goToWelcome();
//             } else {
//                 //TODO dodac jakis powiadomienia ze cos po stronie serwera poszlo nie tak
//             }
//         },
//     });
// }
