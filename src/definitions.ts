export interface AccountKitPlugin {
  signIn(): Promise<{ username: string,huaweiOpenId:string }>;
  signOut(): Promise<void>;
}
