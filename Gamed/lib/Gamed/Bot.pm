use Gamed::Player;
use Gamed::Personality;

class Gamed::Bot is Gamed::Client;

has Gamed::Personality $.personality;

method join_game( $server ) { ... }
method handle_message( $server, %msg ) { ... }
method quit_game( $server ) { ... }