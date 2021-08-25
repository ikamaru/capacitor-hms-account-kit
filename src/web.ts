import { WebPlugin } from '@capacitor/core';

import type { AccountKitPlugin } from './definitions';

export class AccountKitWeb extends WebPlugin implements AccountKitPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
