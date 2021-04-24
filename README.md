# BetaKey
**BungeeCord and Spigot plugin to manage your closed Beta**

Commands:
- "/BetaKey kadd <true/flase>" creates a random key. By clicking on the Key you copy it. Choose true for a permanent key.
- "/BetaKey kremove KEY" deletes a key and you can't use it after that anymore
- "/BetaKey padd NAME" add a player without using a key
- "/BetaKey premove NAME" remove a player from the whitelist (even those with code)
  
Permissions: - BetaKey.command

Website:

Copy the Website in your http directory and change:
 - `$(window).ready(function () {
            <!-- Here must be your path to the Site -->
            history.pushState({}, '/YOUR PATH', window.location.pathname);
          });`
 - `$db = new PDO('mysql:host=localhost;dbname=DATABASE', 'USERNAME', 'PASSWORD');`
          

Discord for Support: https://discord.gg/2aSSGcz

GitHub repo / Issues: https://github.com/Luuuuuis/BetaKey

Questions? Please ask.
Have Fun! ;)
