import {NgModule} from "@angular/core";

import {VoteComponent} from "./vote.component";
import {SharedModule} from "../../shared/shared.module";
import {FooterModule} from "../../app/footer/footer.module";
import {VoteRoutesModule} from "./vote.routes";

/**
 * The main voting page.
 */
@NgModule({
    imports: [
        SharedModule,
        FooterModule,
        VoteRoutesModule
    ],
    declarations: [
        VoteComponent,
    ],
    exports: [],
    providers: [],
})
export class VoteModule {
}
