import {NgModule} from "@angular/core";
import {AlertComponent} from "./alert/alert.component";
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {RouterModule} from "@angular/router";
import {CardModule} from "./card/card.module";
import {CheckboxModule} from "./checkbox/checkbox.module";
import {AnalyticsModule} from "./analytics/analytics.module";

/**
 * This module is dedicated to highly re-usable components that are used often in feature components (pages, etc)
 */
@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        RouterModule,

        // Component modules
        AnalyticsModule,
        CardModule,
        CheckboxModule,
    ],
    declarations: [
        AlertComponent,
    ],
    exports: [
        // Common modules used throughout the application
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        RouterModule,

        // Component modules
        AnalyticsModule,
        CardModule,
        CheckboxModule,

        // Components
        AlertComponent,
    ],
    providers: [],
})
export class SharedModule {
}
