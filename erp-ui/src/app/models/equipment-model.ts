import {Department} from "./department-model";
import {Piece} from "./piece-model";
import {Intervention} from "./intervention-model";

export class Equipment{
  id!:string;
  name!:string;
  description!:string;
  dateMiseEnService!:Date;
  departmentId!:Department;
  pieces!:Piece[];
  interventions!: Intervention[];
}
