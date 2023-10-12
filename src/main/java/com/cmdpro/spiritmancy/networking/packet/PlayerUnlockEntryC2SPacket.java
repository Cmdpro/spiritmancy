package com.cmdpro.spiritmancy.networking.packet;

import com.cmdpro.spiritmancy.Spiritmancy;
import com.cmdpro.spiritmancy.integration.bookconditions.BookKnowledgeCondition;
import com.cmdpro.spiritmancy.moddata.ClientPlayerData;
import com.cmdpro.spiritmancy.moddata.PlayerModData;
import com.cmdpro.spiritmancy.moddata.PlayerModDataProvider;
import com.klikli_dev.modonomicon.book.conditions.BookCondition;
import com.klikli_dev.modonomicon.data.BookDataManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.function.Supplier;

public class PlayerUnlockEntryC2SPacket {
    private final ResourceLocation entry;
    private final ResourceLocation book;

    public PlayerUnlockEntryC2SPacket(ResourceLocation entry, ResourceLocation book) {
        this.entry = entry;
        this.book = book;
    }

    public PlayerUnlockEntryC2SPacket(FriendlyByteBuf buf) {
        this.entry = buf.readResourceLocation();
        this.book = buf.readResourceLocation();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeResourceLocation(entry);
        buf.writeResourceLocation(book);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            supplier.get().getSender().getCapability(PlayerModDataProvider.PLAYER_MODDATA).ifPresent(data -> {
                BookCondition condition = BookDataManager.get().getBook(book).getEntry(entry).getCondition();
                if (condition instanceof BookKnowledgeCondition condition2) {
                    if (data.getKnowledge() > condition2.knowledge) {
                        if (data.getUnlocked().containsKey(book)) {
                            if (!data.getUnlocked().get(book).contains(entry)) {
                                data.getUnlocked().get(book).add(entry);
                                data.setKnowledge(data.getKnowledge() - condition2.knowledge);
                            }
                        } else {
                            ArrayList list = new ArrayList<>();
                            list.add(entry);
                            data.getUnlocked().put(book, list);
                            data.setKnowledge(data.getKnowledge() - condition2.knowledge);
                        }
                    }
                }
            });
        });
        return true;
    }
}