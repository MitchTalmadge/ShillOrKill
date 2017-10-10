import {NgModule} from "@angular/core";

import {VoteComponent} from "./vote.component";
import {SharedModule} from "../../shared/shared.module";
import {FooterModule} from "../../app/footer/footer.module";
import {VoteRoutesModule} from "./vote.routes";
import {HeaderModule} from "../../app/header/header.module";
import {TweetComponent} from "./tweet/tweet.component";

/**
 * The main voting page.
 */
@NgModule({
    imports: [
        SharedModule,
        HeaderModule,
        FooterModule,
        VoteRoutesModule
    ],
    declarations: [
        VoteComponent,
        TweetComponent
    ],
    exports: [],
    providers: [],
})
export class VoteModule {
}
