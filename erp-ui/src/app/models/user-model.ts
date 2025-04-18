import {Poste} from './poste-model';
import {UserRole} from './userRole-model';

export class User{
 id!:number;
 name!:string;
 surname!:string;
 password!:string;
 email!:string;
 naissance!:Date;
 cin!:number;
 departmentId!:string;
 poste!:Poste;
 categorie!:string;
 phoneNumber!:string;
 role!:UserRole;
 adresse!:string
}
