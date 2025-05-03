import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {EquipmentService} from "../services/equipment-service.service";
import {ParameterTypeService} from "../services/parameter-type.service";
import {DailyParameterService} from "../services/daily-parameter.service";
import {Equipment} from "../models/equipment-model";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-production',
  standalone: true,
  imports: [
    NgForOf
  ],
  templateUrl: './production.component.html',
  styleUrl: './production.component.css'
})
export class ProductionComponent implements OnInit {
  constructor(private route: ActivatedRoute,
              private equipmentService: EquipmentService,
              private parameterTypeService:ParameterTypeService,
              dailyParameterService:DailyParameterService) {
  }
  departmentId!:any
  equipments!:Equipment[]

  ngOnInit(): void {
  this.departmentId = this.route.snapshot.paramMap.get('id');
  this.getEquipmentByDepartementId(this.departmentId)
  }

getEquipmentByDepartementId(departmentId:string){
    this.equipmentService.getEquipmentByDepartmentId(departmentId).subscribe({
      next:(data)=>this.equipments=data,
      error:(err)=>err.error().message()
    })
}




}
