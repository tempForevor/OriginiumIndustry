package com.apcp.originium_industry.data.tectree;

import com.apcp.originium_industry.OIMod;
import com.apcp.originium_industry.api.tectree.OITecInfo;
import com.apcp.originium_industry.api.tectree.TecTree;
import javafx.util.Pair;

public class OITecTreeItems {

    public static OITecInfo SPACETIME_SERIALIZE = new OITecInfo(OIMod.id("spacetime_serialize"));
    public static OITecInfo FINAL = new OITecInfo(OIMod.id("final"));
    public static void init(TecTree tree){
        SPACETIME_SERIALIZE.register(tree);
        FINAL.addDependencies(SPACETIME_SERIALIZE).register(tree);
    }
    public static void initTranslation(){
        SPACETIME_SERIALIZE.lang.setLang(new Pair<>("Spacetime Serialize", """
                    By serializing spacetime, we can reshape it.
                    But reshaping it invites an irreversible [DATA-CENSORED] — one we cannot endure.
                    Yet with the apocalypse bearing down, we have no other choice."""))
                .setLang("zh_cn", new Pair<>("时空序列化重塑", """
                    通过对时空的序列化，我们得以重塑时空本身。
                    然而，对时空本身的重塑将引致无可逆的[DATA-CENSORED]——此般代价，我们无法承受。
                    但在步步紧逼的终焉面前，我们别无选择。"""))
                .defaultApply(SPACETIME_SERIALIZE);
        FINAL.lang.setLang(new Pair<>("Great work", """
                    It is done.
                    
                    -!- COMMUNICATION ESTABLISHED -!-
                    Every great work requires its creators.
                    The end of civilization has come. In the aftermath of catastrophe, we fought to survive against all odds.
                    Yet the [Observer] is indifferent — mercilessly extinguishing every possibility of survival we could muster. No matter how hard we fought, we could not escape the timeline of annihilation.
                    One project after another fell. We were forced to admit: we were already beyond salvation. Everything we did was meaningless.
                    As early as the first stars dimmed, the first galaxies fell, and the first black holes evaporated, the [Observer]'s scourge had already eliminated 97% of all individuals. Then came the radiance of the apocalypse, growing ever brighter, its power ever more terrifying.
                    The Main Sector Fleet perished in less than a single day. The Central Sector Defense Array held for barely a month — and bought no more than a thousand survivors.
                    The [AriEta-42] Silent Defense System proved effective. For a moment, we saw hope.
                    But on the very second day after deployment, extinction caught up with us. The post-mortem analysis was devastating: the very [Orc-Iota] radiation emitted by our own high-technology civilization had compromised [AriEta-42]'s silent barrier.
                    Perhaps it could have been refined. But we had no time left. He would not allow us that time.
                    With no alternatives, we gambled everything. The [Preserver] Initiative was passed unanimously. Half of the remaining 243 were immediately assigned to the task.
                    The other half dedicated themselves to preserving as much as possible beyond the end — even if only as proof that we once existed.
                    
                    In the course of our research, we inadvertently uncovered the mechanism of the [Observer]'s destruction: hypersensitivity to [Ori-Iota] radiation, and repulsion from the [Uni-Eps] membrane-bubble space.
                    We quickly synthesized the prototype [Ori-Eps] — the "Origin Stone." Tests succeeded. It possessed suppression capabilities far exceeding [AriEta-42], along with near-miraculous information-to-mass-energy conversion.
                    But there was a catch: it was closed. It could not shield entities outside itself. It could only protect those it devoured — beings transferred into its internalized universe.
                    Even so, we could have retreated into that internal universe, buying time until we found a countermeasure against the [Observer].
                    Yet time — a blade without edge — erased that possibility. By the time we completed the Origin Stone, only 13 of us remained. The [Preserver] Initiative had failed. We overestimated our ability to harvest energy, and underestimated AriEta-42's insatiable demand.
                    Now, our only hope lies with the next civilization.
                    
                    We integrated the [Ori-Eps] Origin Stone with [AriEta-42]. Harnessing the Stone's [Eps-Inverse Excitation] and [Iota-Assimilation] properties, we siphoned colossal energies from the space-time continuum and molded them into a planetary shield — one capable of providing lasting protection for a nascent civilization.
                    Through the Origin Stone's internalized universe, we encoded our technological legacy, using it to influence the planet's biosphere and catalyze rapid developmental acceleration.
                    Finally, we channeled stellar energy from a neighboring galaxy into the Origin Stone, providing the initial power to start the cycle, then entered the internalized universe in cryogenic slumber — awaiting the awakening of the next civilization.
                    
                    And so, newborn civilization: if you are reading this, you have surely completed what once existed only in our speculations — the [Eta-Recursive Amplifier] — and applied it to the primordial Origin Stone.
                    Then it is time. Activate the [Eta-Recursive Amplifier]. Target: [Uni-Eps] membrane-bubble space. Multiplier: 105625.
                    
                    Go forth. And may you succeed where we could not.
                    """))
                .setLang("zh_cn",new Pair<>("伟大之作", """
                        完成了。
                        
                        -!-通讯已建立-!-
                        一切伟大之作都必需人来创造。
                        文明的终焉已然降临，在灾难的余波中，我们绝地求生。
                        然而，[观察者]是冷漠的，祂无情的掐灭了我们任何一个求生的可能性。纵使我们拼尽全力，也无法跳出毁灭的时间线。
                        在一个又一个项目的覆灭里，我们不得不承认，我们早已无力回天，我们的所做全无意义。
                        早在第一颗恒星熄灭，第一个星系陨落，第一个黑洞蒸发的初期，[观察者]所投下的毁灭就已清除了97%的个体。接着，天启的光辉逐渐闪耀，祂的力量也越发骇人。
                        主星系星区防卫舰队在不到1天的时间内全体殉亡，中央星区防卫阵列仅仅坚持了1个月，而这也只换来了不到1000人的幸存。
                        [AriEta-42]静默防卫系统被证明有效，我们似乎看到了希望。
                        可就在[AriEta-42]系统部署的第二天，毁灭就跟上了我们。事后的调查令我们绝望：高等科技的文明本身所散发的[Orc-Iota]辐射摧毁了AriEta-42的静默防御。
                        它或许还有改进的可能，但我们已经等不起了。祂不会容许我们等下去。
                        别无选择，我们只能放手一搏。[保存者]计划全票通过，剩余的243人的一半立刻投入了这项工作。
                        而余下的一半则尽力研究如何让更多东西留存到毁灭之后，哪怕只是作为我们存在的证明。
                        
                        研究之中，我们无意间发现了[观察者]的毁灭机制：对[Ori-Iota]的高敏反应，和对[Uni-Eps]膜泡空间的排斥性。
                        我们迅速制作了实验品[Ori-Eps]“源石”。实验通过，它拥有比[AriEta-42]更为强大的抑制能力，以及接近魔幻的信息-质能转换能力。
                        只可惜，它是封闭的。它不能保护它所包裹的实体，它只能保护被它吞噬的，被转换到内化宇宙中的实体。
                        即使如此，我们也有能力逃进内化宇宙之中，直到研究出对抗[观察者]的对策。
                        可时间如一把无形的利刃，抹杀了这一可能。在研究出源石的同时，我们也只剩下13人幸存。[保存者]计划失败了，我们高估了自己获取能源的能力，也低估了[AriEta-42]的能源需求。
                        现在，我们只能把希望寄托与下一文明。
                        
                        我们将[Ori-Eps]“源石”与[AriEta-42]结合，利用源石的[Eps-逆激化]和[Iota-同化]能力从时空连续体中偷取巨量的能量，并将其塑造成一个包裹整个星球的护罩，足以为新生的文明提供持久的保护。
                        通过源石的内化宇宙，我们编码入我们的科技样本，并以此影响该星球的生物，催化他们在短时间内进行飞速的发展。
                        最后，我们将另一星系的恒星能量注入源石之中，提供初始能量，启动循环，并将自己送入内化宇宙之中沉眠，等待下一文明的苏醒。
                        
                        到此，新生的文明。当你看到这一消息的时候，你应当完成了那个先前只存在于我们设想中的[Eta-递归放大器]，并将其作用到了最初的源石之上。
                        那么，是时候了。启动[Eta-递归放大器]，目标设置：[Uni-Eps]膜泡空间，倍率：105625。
                        
                        放手去做吧。
                        """))
                .defaultApply(FINAL);
    }
}

