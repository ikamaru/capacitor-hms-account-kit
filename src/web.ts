import { WebPlugin } from '@capacitor/core';

import type { AccountKitPlugin } from './definitions';

export class AccountKitWeb extends WebPlugin implements AccountKitPlugin {
  signIn(): Promise<{ username: string; huaweiOpenId: string; }> {
    throw new Error('Method not implemented.');
  }
  signOut(): Promise<void> {
    throw new Error('Method not implemented.');
  }
}
