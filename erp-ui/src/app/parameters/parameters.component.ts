import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {DatePipe, NgClass, NgForOf} from '@angular/common';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';
import { DailyParameterService } from '../services/daily-parameter.service';
import { ParameterTypeService } from '../services/parameter-type.service';
import { EquipmentService } from '../services/equipment-service.service';
import { ParameterType } from '../models/parameterType';
import { Equipment } from '../models/equipment-model';
import { DailyParameter } from '../models/dailyParameter';
import { Position } from '../models/Position';
import { Unit } from '../models/unit';
import { ParamType } from '../models/ParamType';

@Component({
  selector: 'app-parameters',
  standalone: true,
  imports: [NgForOf, FormsModule, DatePipe, NgClass],
  templateUrl: './parameters.component.html',
  styleUrls: ['./parameters.component.css']
})
export class ParametersComponent implements OnInit {
  parameters: ParameterType[] = [];
  newParameter: ParameterType = {} as ParameterType;
  departmentId!: string;
  equipments: Equipment[] = [];
  dailyParameters: DailyParameter[] = [];
  newDailyParameter!: DailyParameter;
  selectedEquipmentId: string = '';
  selectedParameter!: ParameterType;
  positionEnum: Position[] = [];
  unitEnum: Unit[] = [];
  parmaTypeEnum: ParamType[] = [];
  parameterType!: ParameterType

  constructor(
    private router: Router,
    private activeRoute: ActivatedRoute,
    private dailyParameterService: DailyParameterService,
    private typeParametersService: ParameterTypeService,
    private equipmentService: EquipmentService
  ) {
  }

  ngOnInit(): void {
    this.departmentId = this.activeRoute.snapshot.paramMap.get('id')!;
    this.getEquipmentByDepartmentsId(this.departmentId);
    this.positionEnum = this.getPositions();
    this.unitEnum = this.getUnits();
    this.parmaTypeEnum = this.getParamTypes();
   this.getParametersByDepartmentId(this.departmentId);
  }

  openAddParameterModal() {
    this.newParameter = {} as ParameterType;
  }

  getParmetersByEquipmentId(equipmentId: string) {
    this.selectedEquipmentId = equipmentId;
    this.typeParametersService.getByEquipmentId(equipmentId).subscribe({
      next: (data) => {
        this.parameters = data;
        this.dailyParameters = [];

        for (let param of this.parameters) {
          this.getDailyParameters(param);
        }
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des paramètres :', err);
      }
    });
  }


  getParametersByDepartmentId(id: string) {
    this.typeParametersService.getByDepartmentId(id).subscribe({
      next: (data) => {
        this.parameters = data;
        this.dailyParameters = [];

        for (let param of this.parameters) {
          this.getDailyParameters(param);
        }
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des paramètres :', err);
      }
    });
  }

  getEquipmentByDepartmentsId(id: string) {
    this.equipmentService.getEquipmentByDepartmentId(id).subscribe({
      next: (data) => {
        console.log('Équipements reçus :', data); // <-- Vérifie ici
        this.equipments = data;
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des équipements :', err);
      }
    });
  }

  getPositions(): Position[] {
    return Object.values(Position);
  }

  getUnits(): Unit[] {
    return Object.values(Unit);
  }

  getParamTypes(): ParamType[] {
    return Object.values(ParamType);
  }

  getDailyParameters(paramType: ParameterType) {
    this.selectedParameter = paramType
    this.dailyParameterService.getByParameterTypeId(paramType.id).subscribe({
      next: (data) => {
        this.dailyParameters = data;
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des paramètres journaliers :', err);
      }
    });
  }

  supprimererParameter(id: number) {
    Swal.fire({
      title: 'Confirmation',
      text: `Voulez-vous vraiment supprimer  ${id} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui, supprimer',
      cancelButtonText: 'Annuler'
    }).then((result) => {
      if (result.isConfirmed) {
        this.typeParametersService.delete(id).subscribe({
          next: () => {
            this.getParamTypes();
            Swal.fire('Supprimé!', `${id} a été supprimé avec succès.`, 'success');

          },
          error: (err) => {
            Swal.fire('Erreur', err.error?.message || 'Erreur inconnue lors de la suppression.', 'error');
          }
        });
      }
    });
  }

  onSubmitParameter() {
    if (!this.selectedEquipmentId) {
      alert('Aucun équipement sélectionné.');
      return;
    }

    const paramToSend: ParameterType = {
      ...this.newParameter,
      equipmentId: this.selectedEquipmentId
    };

    Swal.fire({
      title: 'Confirmation',
      text: `Voulez-vous vraiment créer le paramètre ${this.newParameter.name || 'sans nom'} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui, Ajouter',
      cancelButtonText: 'Annuler'
    }).then((result) => {
      if (result.isConfirmed) {
        this.typeParametersService.create(paramToSend).subscribe({
          next: () => {
            Swal.fire('Ajouté!', 'Le paramètre a été ajouté avec succès.', 'success');
            this.getParmetersByEquipmentId(this.selectedEquipmentId);
            this.newParameter = {} as ParameterType;
          },
          error: (err) => {
            Swal.fire('Erreur', err.error?.message || 'Erreur inconnue lors de l\'ajout.', 'error');
          }
        });
      }
    });
  }

  getPrameterTypeById(id: number) {
    this.typeParametersService.getById(id).subscribe({
      next: (data) => {
        this.parameterType = data
        this.newParameter = this.parameterType as ParameterType;

      },
      error: (err) => {
        err.error()
      }
    })

  }

  onSubmitDailyParameter() {
    if (!this.selectedEquipmentId) {
      alert('Aucun équipement sélectionné.');
      return;
    }

    const dailyParamToSend: DailyParameter = {
      ...this.newDailyParameter,
      parameterType: this.selectedParameter,
      date: new Date(),
      observation:'accpetable'
    };

    Swal.fire({
      title: 'Confirmation',
      text: `Voulez-vous vraiment créer le DAILY paramètre ${this.newDailyParameter || 'sans nom'} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui, Ajouter',
      cancelButtonText: 'Annuler'
    }).then((result) => {
      if (result.isConfirmed) {
        if (dailyParamToSend.value > dailyParamToSend.parameterType.max) {
          dailyParamToSend.observation = 'élevée';
        } else if (dailyParamToSend.value < dailyParamToSend.parameterType.min) {
          dailyParamToSend.observation = 'faible';
        } else {
          dailyParamToSend.observation = 'acceptable';
        }
        this.dailyParameterService.create(dailyParamToSend).subscribe({
          next: () => {
            console.log(dailyParamToSend)
            Swal.fire('Ajouté!', 'Le paramètre a été ajouté avec succès.', 'success');
            this.getParmetersByEquipmentId(this.selectedEquipmentId);
            this.newDailyParameter = {} as DailyParameter;
          },
          error: (err) => {
            Swal.fire('Erreur', err.error?.message || 'Erreur inconnue lors de l\'ajout.', 'error');
          }
        });
      }
    });
  }


  openAddDailyParmeterModal() {
    this.newDailyParameter ={} as DailyParameter;
    };

}
