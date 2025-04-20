import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { StaffResponse } from "../type/staffResponseType";

@Injectable({providedIn: 'root'})
export class StaffService {
    constructor(private http: HttpClient){ }

    getStaffs(page: number, size: number): Observable<any>{
        const param = new HttpParams()
        .set("pageNo", page.toString())
        .set("size",size.toString())
        return this.http.get<any>(`http://localhost:8080/api/staff/getListStaff?${param}`)
    }

    createStaff(staffRespone: StaffResponse): Observable<any>{
        return this.http.post<any>(`http://localhost:8080/api/staff/createStaff`, staffRespone)
    }

    updateStaff(idStaff: string, staffRespone: StaffResponse): Observable<any>{
        return this.http.put<any>(`http://localhost:8080/api/staff/updateStaff/${idStaff}`, staffRespone)
    }

    getOneStaff(idStaff: string): Observable<any>{
        return this.http.get<any>(`http://localhost:8080/api/staff/getOneStaff/${idStaff}`)
    }

    deleteStaff(idStaff: string): Observable<any>{
        return this.http.delete<any>(`http://localhost:8080/api/staff/changeStatus/${idStaff}`)
    }

    exportStaff(): Observable<Blob>{
        return this.http.get(`http://localhost:8080/api/staff/export`, {
            responseType: 'blob'
          })
    }

    importStaff(formData: FormData): Observable<any>{
        return this.http.post<any>(`http://localhost:8080/api/staff/import`, formData)
    }

    timeImportStaff(): Observable<any>{
        return this.http.get<any>(`http://localhost:8080/api/staff/import-history`)
    }

}

