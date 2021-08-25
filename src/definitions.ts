export interface AccountKitPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
