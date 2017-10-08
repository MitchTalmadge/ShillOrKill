import {NgModule} from "@angular/core";

import {HeaderComponent} from "./header.component";
import {CommonModule} from "@angular/common";
import {SharedModule} from "../../shared/shared.module";

@NgModule({
    imports: [
        CommonModule,
        SharedModule
    ],
    declarations: [
        HeaderComponent,
    ],
    exports: [
        HeaderComponent
    ],
    providers: [],
})
export class HeaderModule {
}
