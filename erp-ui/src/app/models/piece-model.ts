import {Intervention} from "./intervention-model";
import {Category} from "./category";
import {Equipment} from "./equipment-model";

export class Piece{
  id!: string;
  name!: string;
  sku!: string;
  price!: number;
  stockQuantity!: number;
  description!: string;
  expiryDate!: Date;
  updatedAt!: Date;
  createdAt!: Date;
  equipmentId!: Equipment;
  categorieId!: Category;
  interventions!: Intervention[];
}
