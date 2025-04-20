import { DatePipe, NgClass, NgFor, NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { StaffApi } from '../../type/staffApiType';
import { StaffService } from '../../service/staff.service';
import { error } from 'console';
import { StaffResponse } from '../../type/staffResponseType';
import { FormBuilder, FormGroup, FormsModule, NgModel, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterOutlet } from '@angular/router';
import { importTimeApi } from '../../type/importTimeApi';

@Component({
  selector: 'app-home',
  imports: [NgFor, NgClass, NgIf, FormsModule, ReactiveFormsModule, DatePipe],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
  addStaffForm: FormGroup;
  updateStaffForm: FormGroup;
  uploadStaffForm: FormGroup;
  // hien thi danh sach nhan vien
  staffs: StaffApi[] = [];
  totalPage: number = 0;
  currentPage: number = 1;
  pageSize: number = 5;
  visiblePages: number[] = [];

  constructor(private staffService: StaffService,
              private fb: FormBuilder,
              private router: Router
  ){
    this.addStaffForm = this.fb.group({
      ma: ['', Validators.required],
      name:['', Validators.required],
      fe:['',Validators.required],
      fpt:['',Validators.required]
    })
    this.updateStaffForm = this.fb.group({
      ma: ['', Validators.required],
      name:['', Validators.required],
      fe:['',Validators.required],
      fpt:['',Validators.required]
    })
    this.uploadStaffForm = this.fb.group({
      fileUp:[null, Validators.required]
    })
  }

  // xu li update nhan vien
  isUpdateStaffModalVisible:boolean = false
  id: string = '';
  closeUpdateStaffModal(){
    this.isUpdateStaffModalVisible = false;
    this.resetForm();
    this.id = '';
  }
  openUpdateStaff(staff: StaffApi){
    this.isUpdateStaffModalVisible = true;
    this.id = staff.id
    this.newStaff = {...staff}
    this.updateStaffForm.patchValue({
      ma: staff.ma,
      name: staff.name,
      fpt: staff.fpt,
      fe: staff.fe
    });
  }

  updateStaff(){
    if(this.updateStaffForm.valid){
      this.newStaff = {
        ...this.newStaff,
        ...this.updateStaffForm.value
      }
      this.staffService.updateStaff(this.id,this.newStaff).subscribe({
        next: (res: any) =>{
          this.resetForm()
          this.isUpdateStaffModalVisible = false;
          this.getAllstaffs(this.currentPage, this.pageSize);
          alert('Sua nhan vien thanh cong')
        },
        error: (error)=>{
          alert(error)
        }
      })
    }else{
      alert(' sai Form update nhan vien')
    }
  }
  // hien thi nhan vien
  onChangePage(page: number): void {
    this.currentPage = page;
    this.getAllstaffs(this.currentPage, this.pageSize);
  }

  generateVisiblePages(currentPage: number, totalPages: number): number[] {
    const visiblePages: number[] = [];
    const maxVisible = 5;
    const startPage = Math.max(1, currentPage - Math.floor(maxVisible / 2));
    const endPage = Math.min(totalPages, startPage + maxVisible);

    for (let i = startPage; i < endPage; i++) {
      visiblePages.push(i);
    }
    return visiblePages;
  }

  getAllstaffs(pageNo: number, size: number): void{
    this.staffService.getStaffs(pageNo-1, size).subscribe({
      next: (res: any) => {
        this.staffs = res.staffs
        this.totalPage = res.totalPage
        this.visiblePages = this.generateVisiblePages(this.currentPage, this.totalPage);
      },
      error: (error: any) =>{
        alert(error)
      }
    })
  }
  ngOnInit(): void {
    this.getAllstaffs(this.currentPage, this.pageSize)
  }

  // xu li thay doi trang thai
  staff: any = null
  isConfirmDialogVisible: boolean = false

  openConfirm(staff: any): void {
    this.staff = staff;
    this.isConfirmDialogVisible = true;
  }
  cancelChange(): void {
    this.isConfirmDialogVisible = false;
    this.staff = null;
  }
  
  confirmChange(): void {
    console.log(this.staff.id)
    this.staffService.deleteStaff(this.staff.id).subscribe({
      next: (res) => {
        console.log('Xóa thành công', res);
        this.getAllstaffs(this.currentPage, this.pageSize);
      },
      error: (err) => {
        console.error('Lỗi khi xóa nhân viên:', err);
        alert('Đã xảy ra lỗi khi xóa nhân viên!');
      }
    });
    this.isConfirmDialogVisible = false;
    this.staff = null;
  }

  // xu li add nhan vien
  isAddStaffModalVisible: boolean = false
  newStaff: StaffResponse = {
    ma: '',
    name: '',
    fe: '',
    fpt: '',
    status: 1
  }
  openAddStaff(){
    this.isAddStaffModalVisible = true;
  }
  closeAddStaffModal(){
    this.isAddStaffModalVisible = false;
    this.resetForm()
  }
  resetForm(){
    this.newStaff = {
      ma: '',
      name: '',
      fe: '',
      fpt: '',
      status: 1
    }
  }

  saveStaff(){
    if(this.addStaffForm.valid){
      this.newStaff = {
        ...this.newStaff,
        ...this.addStaffForm.value
      }
      console.log(this.newStaff)
      this.staffService.createStaff(this.newStaff).subscribe({
        next: (res: any) =>{
          this.resetForm()
          this.isAddStaffModalVisible = false;
          this.getAllstaffs(this.currentPage, this.pageSize);
          alert('Them nhan vien thanh cong')
        },
        error: (error)=>{
          alert(error)
        }
      })
    }else{
      alert(' sai Form them nhan vien')
    }
  }

  // xu li detail
  openDetail(idStaff: string){
    this.router.navigate(['/detail', idStaff])
  }

  // export staffs
  exportStaffs() {
    this.staffService.exportStaff().subscribe({
      next: (blob: Blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'nhanvien.xlsx';
        a.click();
        window.URL.revokeObjectURL(url);
      },
      error: (err) => {
        console.error('Lỗi khi xuất nhân viên:', err);
        alert('Đã xảy ra lỗi khi xuất nhân viên!');
      }
    });
  }

  // import staffs
  isUploadStaffModalVisible: boolean = false;
  selectedFile: File | null = null;
  openImportStaffs(){
    this.isUploadStaffModalVisible = true;
    this.selectedFile = null;
  }

  closeUpdateLoad(){
    this.isUploadStaffModalVisible = false;
  }
  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
      this.uploadStaffForm.patchValue({
        fileUp: this.selectedFile
      });
      console.log('File đã chọn:', this.selectedFile)
      this.uploadStaffForm.get('fileUp')?.updateValueAndValidity();
    }
  }

  uploadStaff(){
    if(this.uploadStaffForm.valid && this.selectedFile){
      const formData = new FormData();
      formData.append('file', this.selectedFile)

      this.staffService.importStaff(formData).subscribe({
        next:() =>{
          this.isUploadStaffModalVisible = false;
          alert('upload thanh cong');
          this.getAllstaffs(this.currentPage, this.pageSize);
          this.selectedFile = null;
        },
        error:(error) =>{
          alert('Import thanh cong')
          this.isUploadStaffModalVisible = false;
          this.getAllstaffs(this.currentPage, this.pageSize);
          this.selectedFile = null;
        }
      })
    }else{
      alert('Vui long chon file Excel')
    }
  }

  // xu li lich su import 
  showModal: boolean = false;
  listTimeImport: importTimeApi[]=[];
  openTimeImport(){
    this.showModal = true;
    this.staffService.timeImportStaff().subscribe({
      next: (res: any) =>{
          this.listTimeImport  = res
      },
      error: (error) =>{
        alert('loi hien thi');
      }
    })
  }
}
