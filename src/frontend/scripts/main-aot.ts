import {platformBrowser} from '@angular/platform-browser';
import {AppModuleNgFactory} from '../../../lib/aot/scripts/app.module.ngfactory';
import {enableProdMode} from '@angular/core';

enableProdMode();

platformBrowser().bootstrapModuleFactory(AppModuleNgFactory);