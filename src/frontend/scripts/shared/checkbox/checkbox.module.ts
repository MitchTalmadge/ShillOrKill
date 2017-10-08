import {NgModule} from "@angular/core";

import {CheckboxComponent} from "./checkbox.component";
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule
    ],
    declarations: [
        CheckboxComponent
    ],
    exports: [
        CheckboxComponent
    ],
    providers: [],
})
export class CheckboxModule {
}
