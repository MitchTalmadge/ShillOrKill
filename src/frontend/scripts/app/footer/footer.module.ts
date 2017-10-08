import {NgModule} from "@angular/core";

import {FooterComponent} from "./footer.component";
import {CommonModule} from "@angular/common";
import {SharedModule} from "../../shared/shared.module";

@NgModule({
    imports: [
        CommonModule,
        SharedModule
    ],
    declarations: [
        FooterComponent,
    ],
    exports: [
        FooterComponent
    ],
    providers: [],
})
export class FooterModule {
}
