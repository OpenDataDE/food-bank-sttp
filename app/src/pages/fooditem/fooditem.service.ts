import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/map';
import { environment } from '../../environments/environment';
import { Product } from '../foodcategory/product';

@Injectable()
export class FoodItemService {

		private category: string;
		private _productUrl = environment.uri + this.category;

		constructor(private _http: Http) {}

		public getProducts(): Observable<Product[]> {
				return this._http.get(this._productUrl)
						.map((response: Response) => <Product[]> response.json())
						.catch(this.handleError);
		}

		private handleError(error: Response) {
				console.error(error);
				return Observable.throw(error.json().error || 'Server error');
		}

}
