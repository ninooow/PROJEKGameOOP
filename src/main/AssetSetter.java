package main;

import entity.NPC1;
import entity.NPC2;
import entity.NPC3;
import entity.NPC4;
import object.OBJ_Gold;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        // GOLD1
        gp.goldList.add(new OBJ_Gold(gp, gp.tileSize * 27, gp.tileSize * 5));

        // GOLD2
        gp.goldList.add(new OBJ_Gold(gp, gp.tileSize * 40, gp.tileSize * 16));

        // GOLD3
        gp.goldList.add(new OBJ_Gold(gp, gp.tileSize * 42, gp.tileSize * 21));

        // GOLD4
        gp.goldList.add(new OBJ_Gold(gp, gp.tileSize * 42, gp.tileSize * 21));

        // GOLD5
        gp.goldList.add(new OBJ_Gold(gp, gp.tileSize * 12, gp.tileSize * 27));
    }



    public void setNPC() {
        gp.npc[0] = new NPC1(gp);
        gp.npc[0].worldX = gp.tileSize * 18;
        gp.npc[0].worldY = gp.tileSize * 3;
        gp.npc[1] = new NPC2(gp);
        gp.npc[1].worldX = gp.tileSize * 42;
        gp.npc[1].worldY = gp.tileSize * 3;
        gp.npc[2] = new NPC3(gp);
        gp.npc[2].worldX = gp.tileSize * 27;
        gp.npc[2].worldY = gp.tileSize * 17;
        gp.npc[3] = new NPC4(gp);
        gp.npc[3].worldX = gp.tileSize * 36;
        gp.npc[3].worldY = gp.tileSize * 28;
    }
}
