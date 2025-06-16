# Discord Music Bot
Streams any YouTube video to your private Discord server. A simple bot using LavaPlayer and Discord4J.

## Steps To Run Local

### Register Your Application With Discord
1. Create your Discord Application in the Developer Portal. (https://discord.com/developers/applications)
2. Obtain Client Secret from the "Bot" tab.
3. Obtain Application ID and Public Key from the "General Information" tab.
4. Obtain the Guild ID of your Discord Guild.

### Create Local Configuration File

1. Create file `application-local.yml` in `src/main/resources` (gitignored for this purpose)
2. Set the following properties:
    ```
    discord:
      authentication:
        public:
          app-id: <your discord application ID>
          guild-id: <your discord guild ID>
          public-key: <your application public key>
        secret:
          discord-token: <your SUPER secret discord token here>
          oauth-2-token: <your SUPER secret YouTube OAuth token here>
    ```
3. Run Spring with the `local` profile to load your specific properties (`--spring.profiles.active=local`)

### That's it! :) 
When you launch the application, it will automatically install all commands on your server. 
