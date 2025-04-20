import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { smfResponse } from "../type/smfResponse";

@Injectable({providedIn: 'root'})
export class SMFService{
    constructor(private http: HttpClient){
    }

    getSmf(idStaff: string): Observable<any>{
        return this.http.get<any>(`http://localhost:8080/api/smf/getSMF/${idStaff}`)
    }

    getMajor(): Observable<any>{
        return this.http.get<any>(`http://localhost:8080/api/smf/getMajor`)
    }

    getDepartment(): Observable<any>{
        return this.http.get<any>(`http://localhost:8080/api/smf/getDepartment`)
    }

    getFacility(idStaff: string): Observable<any>{
        return this.http.get<any>(`http://localhost:8080/api/smf/getFacility/${idStaff}`)
    }

    createSmf(smfResponse: smfResponse): Observable<any>{
        return this.http.post<any>(`http://localhost:8080/api/smf/createSMF`, smfResponse)
    }

    // getOneStaff(idStaff: string): Observable<any>{
    //     return this.http.get<any>(`http://localhost:8080/api/staff/getOneStaff/${idStaff}`)
    // }

    deleteSmf(idSmf: string): Observable<any>{
        return this.http.delete<any>(`http://localhost:8080/api/smf/deleteSMF/${idSmf}`)
    }

}