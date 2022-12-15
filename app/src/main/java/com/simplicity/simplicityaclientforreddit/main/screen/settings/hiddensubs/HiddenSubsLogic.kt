package com.simplicity.simplicityaclientforreddit.main.screen.settings.hiddensubs

import android.util.Log
import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseLogic
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.io.room.RoomDB
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HiddenSubsLogic : BaseLogic() {
    private val _stateFlow = MutableStateFlow<UiState<String>>(UiState.Loading())
    val stateFlow: StateFlow<UiState<String>> = _stateFlow

    fun start() {
        getListOfHiddenSubs()
        // Superstonk,Minecraft,Genshin_Impact,ich_iel,pokemon,IndianDankMemes,PewdiepieSubmissions,tf2,halo,formula1,Genshin_Impact_Leaks,GlobalOffensive,ik_ihe,Hololive,HollowKnightMemes,dankinindia,traps,pokemongo,SaimanSays,kpop,2007scape,hungary,u_BabiMunizTS,Turkey,VALORANT,Warframe,EscapefromTarkov,FemBoys,HairyPussy,trans,france,twinks,GoneWildTrans,straightturnedgay,transporn,Sissies,Terraria,nba,amcstock,CFB,CollegeBasketball,sports,hockey,de,bigdickgirl,Tgirls,Colts,baseball,guns,thenetherlands,DadsGoneWild,trapsarentgay,boypussy,OnlyIfShesPackin,CryptoCurrency,HydroHomies,tscum,IsThisaGirl,WritingPrompts,traphentai,gay_irl,buffalobills,sissycaptions,ClashOfClans,bigclit,crossdressing,HungTwinks,MassiveCock,TransGoneWild,Gunners,MTFSelfieTrain,gayporn,formuladank,ksi,OnePiece,GOONED,AlphaMalePorn,Shemales,gayfurryporn,foreskin,Hairy,gaybros,portugal,LiverpoolFC,chubby,FurryPornSubreddit,broslikeus,rance,Romania,Jokes,dadjokes,dndnext,DnD,h3h3productions,DestinyTheGame,titanfall,footballmanagergames,bostonceltics,CrusaderKings,NASCAR,golf,Kanye,ufc,F1Game,dndmemes,destiny2,Polska,Eldenring,totalwar,fo4,thesims,MMA,popping,Warhammer40k,deadbydaylight,AskOuija,forhonor,soccercirclejerk,Music,ForzaHorizon,GreenBayPackers,OkBrudiMongo,DBZDokkanBattle,gtaonline,wallstreetbets,GME,shittyrainbow6,Rainbow6,steelers,euphoria,PremierLeague,Firearms,timberwolves,osugame,ValorantCompetitive,Stellaris,NYYankees,IndiaSpeaks,49ers,wow,GMEJungle,nbacirclejerk,ClashRoyale,SmashBrosUltimate,brasil,chelseafc,StardewValley,betterCallSaul,TheLastAirbender,Polska_wpz,Bitcoin,NewVegasMemes,singapore,Sims3,ApexOutlands,FortNiteBR,PunPatrol,UFOs,Denmark,ACMilan,indonesia,tennis,wirklichgutefrage,tf2shitposterclub,warriors,shitpostemblem,Overwatch_Memes,civ,DragonballLegends,BlackPink,reddeadredemption2,AnarchyChess,AnimalCrossing,MinecraftMemes,deutschememes,CricketShitpost,wasletztepreis,motorcycles,yugioh,Cricket,Grimdank,yakuzagames,ontario,Slovenia,forza,Mujico,CallOfDutyMobile,hoi4,jschlatt,FrenchMemes,DotA2,texas,nhl,eu4,nrl,discgolf,FIFA,SquaredCircle,reddevils,Overwatch,FloridaGators,FifaCareers,fantasyfootball,nosleep,Astros,Formula1Point5,apexlegends,DarkTide,factorio,Sekiro,CODWarzone,WWE,nflmemes,paydaytheheist,gaybrosgonemild,Boxing,FORTnITE,motogp,WorldofTanks,StardewMemes,ussoccer
        updateHiddenSubs("Superstonk,Minecraft,Genshin_Impact,ich_iel,pokemon,IndianDankMemes,PewdiepieSubmissions,tf2,halo,formula1,Genshin_Impact_Leaks,GlobalOffensive,ik_ihe,Hololive,HollowKnightMemes,dankinindia,traps,pokemongo,SaimanSays,kpop,2007scape,hungary,u_BabiMunizTS,Turkey,VALORANT,Warframe,EscapefromTarkov,FemBoys,HairyPussy,trans,france,twinks,GoneWildTrans,straightturnedgay,transporn,Sissies,Terraria,nba,amcstock,CFB,CollegeBasketball,sports,hockey,de,bigdickgirl,Tgirls,Colts,baseball,guns,thenetherlands,DadsGoneWild,trapsarentgay,boypussy,OnlyIfShesPackin,CryptoCurrency,HydroHomies,tscum,IsThisaGirl,WritingPrompts,traphentai,gay_irl,buffalobills,sissycaptions,ClashOfClans,bigclit,crossdressing,HungTwinks,MassiveCock,TransGoneWild,Gunners,MTFSelfieTrain,gayporn,formuladank,ksi,OnePiece,GOONED,AlphaMalePorn,Shemales,gayfurryporn,foreskin,Hairy,gaybros,portugal,LiverpoolFC,chubby,FurryPornSubreddit,broslikeus,rance,Romania,Jokes,dadjokes,dndnext,DnD,h3h3productions,DestinyTheGame,titanfall,footballmanagergames,bostonceltics,CrusaderKings,NASCAR,golf,Kanye,ufc,F1Game,dndmemes,destiny2,Polska,Eldenring,totalwar,fo4,thesims,MMA,popping,Warhammer40k,deadbydaylight,AskOuija,forhonor,soccercirclejerk,Music,ForzaHorizon,GreenBayPackers,OkBrudiMongo,DBZDokkanBattle,gtaonline,wallstreetbets,GME,shittyrainbow6,Rainbow6,steelers,euphoria,PremierLeague,Firearms,timberwolves,osugame,ValorantCompetitive,Stellaris,NYYankees,IndiaSpeaks,49ers,wow,GMEJungle,nbacirclejerk,ClashRoyale,SmashBrosUltimate,brasil,chelseafc,StardewValley,betterCallSaul,TheLastAirbender,Polska_wpz,Bitcoin,NewVegasMemes,singapore,Sims3,ApexOutlands,FortNiteBR,PunPatrol,UFOs,Denmark,ACMilan,indonesia,tennis,wirklichgutefrage,tf2shitposterclub,warriors,shitpostemblem,Overwatch_Memes,civ,DragonballLegends,BlackPink,reddeadredemption2,AnarchyChess,AnimalCrossing,MinecraftMemes,deutschememes,CricketShitpost,wasletztepreis,motorcycles,yugioh,Cricket,Grimdank,yakuzagames,ontario,Slovenia,forza,Mujico,CallOfDutyMobile,hoi4,jschlatt,FrenchMemes,DotA2,texas,nhl,eu4,nrl,discgolf,FIFA,SquaredCircle,reddevils,Overwatch,FloridaGators,FifaCareers,fantasyfootball,nosleep,Astros,Formula1Point5,apexlegends,DarkTide,factorio,Sekiro,CODWarzone,WWE,nflmemes,paydaytheheist,gaybrosgonemild,Boxing,FORTnITE,motogp,WorldofTanks,StardewMemes,ussoccer")
    }

    private fun updateHiddenSubs(hiddenSubs: String) {
        background {
            val db = RoomDB()
            db.deleteAllHiddenSubs()
            val array = hiddenSubs.split(",")
            for (sub in array) {
                db.hideSub(sub)
            }
        }
    }

    private fun getListOfHiddenSubs() {
        background {
            val db = RoomDB()
            val listOfHiddenSubs = db.getAllHiddenSubs()
            val stringArray = listOfHiddenSubs.map {
                it.sub
            }
            val joinedString = stringArray.joinToString(",")
            foreground {
                _stateFlow.emit(UiState.Success(joinedString))
                Log.i("HiddenSubsLogic", "Hidden: $joinedString")
            }
        }
    }
}
