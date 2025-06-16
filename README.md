# Discord Music Bot
Streams any YouTube video to your private Discord server. A simple bot using LavaPlayer and Discord4J.

## Steps To Run Local

### 1. Install Java 17 or Greater
1. Amazon Coretto (https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html)
2. Add Java to classpath such that the command `java --version` resolves to >= 17.

### 2. Register Your Application With Discord
1. Create your Discord Application in the Developer Portal. (https://discord.com/developers/applications)
2. Obtain Client Secret from the "Bot" tab.
3. Obtain Application ID and Public Key from the "General Information" tab.
4. Obtain the Guild ID of your Discord Guild.
5. Obtain the Channel ID of your requests channel.

>**Hint**: Find the guild and channel ID easily by using the Discord browser client. Example: https://discord.com/channels/<guild_id>/<channel_id>

### Create Local Configuration File

1. Create file `application-local.yml` in `src/main/resources` (gitignored for this purpose)
2. Set the following properties:
    ```yaml
    discord:
      authentication:
        public:
          request-channel-id: <channel id where music requests are allowed>
          app-id: <your discord application ID>
          guild-id: <your discord guild ID>
          public-key: <your application public key>
        secret:
          discord-token: <your SUPER secret discord token here>
    ```
3. Run Spring with the `local` profile to load your special property file. Use the following command in the repository's root directory.
   ```
   # Linux / Mac / Git Bash
   ./gradlew bootRun --args='--spring.profiles.active=local'
   
   # Windows Command Prompt
   gradlew bootRun --args='--spring.profiles.active=local'
   ```
   
### Finish OAuth Configuration

The first time you run the application, you'll see a log message instructing you to log into YouTube. 
Follow the link and this will generate an "OAuth Refresh Token"; YouTube will now associate
your account with that token. Once you have the refresh token, set the following property in your `application-local.yml`.

   ```yaml
    discord:
      authentication:
        secret:
          oauth-2-token: <your SUPER secret YouTube OAuth Token here>
   ```
> **Hint**: USE A BURNER ACCOUNT. Do not use your main Google account.
> This bot is against YouTube ToS; if they catch you, they can terminate your account.

Now, whenever the bot launches, it will handle YouTube login automatically, no action required from the user.

### That's it! :) 
When you launch the application, it will automatically install all commands on your server. 
