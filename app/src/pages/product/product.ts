export class Product {
	public readonly referenceNumber: number;
	public readonly category: string;
	public readonly description: string;
	public readonly price: number;
	public readonly quantityAvailable: number;

	constructor(product: Object) {
		this.referenceNumber = product["referenceNumber"];
		this.category = product["category"];
		this.description = product["description"];
		this.price = product["price"];
		this.quantityAvailable = product["quantityAvailable"];
	}
}
