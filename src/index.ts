import { registerPlugin } from '@capacitor/core';

import type { AccountKitPlugin } from './definitions';

const AccountKit = registerPlugin<AccountKitPlugin>('AccountKit', {
  web: () => import('./web').then(m => new m.AccountKitWeb()),
});

export * from './definitions';
export { AccountKit };
