import {Intervention} from "./intervention-model";

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
  equipmentId!: string;
  categorieId!: string;
  interventions!: Intervention[];
}
