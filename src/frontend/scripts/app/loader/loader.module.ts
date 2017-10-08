import {NgModule} from "@angular/core";

import {LoaderComponent} from "./loader.component";
import {SharedModule} from "../../shared/shared.module";

@NgModule({
    imports: [
        SharedModule
    ],
    declarations: [
        LoaderComponent
    ],
    exports: [
        LoaderComponent
    ],
    providers: [],
})
export class LoaderModule {
}
