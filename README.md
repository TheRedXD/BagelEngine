# BagelEngine

BagelEngine is a Minecraft fabric mod designed for free-op anarchy servers. It is intended to replace the more
traditional way of forking Paper in order to apply various fixes/patches, while also adding functionality you'd add via
just using the API paper provides you, but instead - using Fabric's API! This approach does two things with one easy,
simple solution. No more messing with some random paper fork and struggling to maintain it due to the sheer amount of
code being added (and changed!) each Minecraft update.

## Is this ready?
Currently, this mod is highly unfinished. If you want to contribute - you're free to make a PR! However, it is currently
not fully suitable for proper production usage, unless you're willing to tweak the source yourself, and compile it
yourself. It is NOT configurable as of 2025-01-04 when I'm writing this, although this README will probably be highly
tweaked once it's suitable for production usage. But for now - PRs are open. The more people help, the faster this gets
done.

## What is the goal?
The goal is NOT to be a single mod for absolutely everything (because there are reasonable fabric alternatives already),
but to provide a reasonable set of tools and patches in order to make the server overall more bearable. Obviously, that
wouldn't fix any of the communities surrounding anarchy (just look at servers like 2b2t lol), but good tooling made to
prevent extreme abuse or extreme sources of lag will definitely help more people mess around with the server. This,
surprisingly, also helps to find new exploits, because people on anarchy communities are more likely to try and break
the server. Ask how I know. Therefore, another goal of this mod is to allow active monitoring of how lag generally
occurs, detection and prevention of various exploits (including crash exploits), possibly some way of monitoring lag on
a chunk-by-chunk basis (maybe a map of sorts?).

Overall, the idea is for this to be a mod designed to patch various exploits and issues you may encounter on anarchy
servers, which can quickly make it entirely unplayable, and a way to easily monitor things like lag. In fact, this mod
already integrates with spark in order for the very basic lag fixer I wrote, `LagFixRoutine`, to work (right now, it
only clears entities based on entity count and TPS automatically). It's not perfect, but the idea is to potentially
integrate some of this functionality in other minecraft servers, learning from things that happen on anarchy servers.

## Why "BagelEngine"?
The name is derived from my own anarchy server I have been building for fun called "Bagel", and the reason why I named
it that way is because I like bagels. Simple as that. And I think it's a funny name either way, so why not call it
bagel? After all, in my culture, we used to make bagels many years ago as a treat, it may as well be inherited. A bagel
is truly the perfect shape and nothing beats it. The Earth is, in fact, a bagel hole, not a sphere.
YOU HAVE BEEN FOOLED BY THE ANTI-BAGEL CONSPIRACY! IT IS ALL A LIE- alright I went on a tangent here and had a bit too
much fun, enjoy the most cursed bagel-powered mod in existence!