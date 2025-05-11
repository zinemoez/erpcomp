import {Department} from "./department-model";
import {Piece} from "./piece-model";
import {Intervention} from "./intervention-model";
import {ParameterType} from "./parameterType";

export class Equipment{
  id!:string;
  name!:string;
  description!:string;
  dateMiseEnService!:Date;
  departmentId!:Department;
  pieces!:Piece[];
  parameterTypes!:ParameterType[];
  interventions!: Intervention[];
}
