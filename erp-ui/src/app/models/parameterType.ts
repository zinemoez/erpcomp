import {DailyParameter} from "./dailyParameter";
import {Equipment} from "./equipment-model";
import {Unit} from "./unit";
import {ParamType} from "./ParamType";
import {Position} from "./Position";


export class ParameterType {
  id!: number;
  name!:string
  unit!:Unit
  paramType!:ParamType
  min!:number
  max!:number
  dailyParameters!:DailyParameter[]
  equipmentId!:string
  position!:Position
}
