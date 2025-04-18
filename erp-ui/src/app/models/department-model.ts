import {Equipment} from "./equipment-model";
import {User} from "./user-model";

export class Department {

 id!: string ;
 name!: string ;
 description!: string ;
 equipmentsId!:Equipment[];
 users!:User[];
 createdAt!: Date ;
}
