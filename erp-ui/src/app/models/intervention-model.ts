import {Status} from './etat-model';
import {Type} from './type-model';
import {Priority} from './priority-model';
import {User} from "./user-model";
import {Piece} from "./piece-model";
import {Equipment} from "./equipment-model";



export class Intervention{
  id!:number
  title!: string;
  equipmentId!: Equipment;
  approvedBy!: User;
  status!: Status;
  type!: Type;
  staffIds!: User[];
  description!: string;
  pieceIds!: Piece[];
  createdAt!: Date;
  createdBy!: User;
  updatedAt!: Date;
  updatedBy!: User;
  priority!: Priority;





}
