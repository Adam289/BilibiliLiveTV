package com.muedsa.bilibililivetv.player.live;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.leanback.media.PlaybackGlue;
import androidx.leanback.widget.AbstractDetailsDescriptionPresenter;
import androidx.leanback.widget.Action;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.PlaybackControlsRow;
import androidx.leanback.widget.PlaybackRowPresenter;

import com.google.android.exoplayer2.ext.leanback.LeanbackPlayerAdapter;
import com.muedsa.bilibililivetv.R;

import java.util.List;

public class PlaybackTransportControlGlue extends androidx.leanback.media.PlaybackTransportControlGlue<LeanbackPlayerAdapter> {

    DanmakuPlayToggleAction danmakuPlayToggleAction;
    ChangePlayUrlAction changePlayUrlAction;
    SuperChatToggleAction superChatToggleAction;
    GiftToggleAction giftToggleAction;

    public PlaybackTransportControlGlue(Context context, LeanbackPlayerAdapter impl) {
        super(context, impl);
    }

    @Override
    protected PlaybackRowPresenter onCreateRowPresenter() {
        PlaybackTransportRowPresenter rowPresenter = new PlaybackTransportRowPresenter(getContext(),
                PlaybackTransportControlGlue.this);
        rowPresenter.setDescriptionPresenter(new AbstractDetailsDescriptionPresenter() {
            @Override
            protected void onBindDescription(ViewHolder
                                                     viewHolder, Object obj) {
                PlaybackTransportControlGlue glue = (PlaybackTransportControlGlue) obj;
                viewHolder.getTitle().setText(glue.getTitle());
                viewHolder.getSubtitle().setText(glue.getSubtitle());
            }
        });
        return rowPresenter;
    }

    @Override
    protected void onCreatePrimaryActions(ArrayObjectAdapter primaryActionsAdapter) {
        super.onCreatePrimaryActions(primaryActionsAdapter);
        Context context = getContext();
        danmakuPlayToggleAction = new DanmakuPlayToggleAction(context);
        danmakuPlayToggleAction.setIndex(DanmakuPlayToggleAction.INDEX_OFF);
        primaryActionsAdapter.add(danmakuPlayToggleAction);

        changePlayUrlAction = new ChangePlayUrlAction(context);
        primaryActionsAdapter.add(changePlayUrlAction);

        superChatToggleAction = new SuperChatToggleAction(context);
        superChatToggleAction.setIndex(SuperChatToggleAction.INDEX_OFF);
        primaryActionsAdapter.add(superChatToggleAction);

        giftToggleAction = new GiftToggleAction(context);
        giftToggleAction.setIndex(GiftToggleAction.INDEX_ON);
        primaryActionsAdapter.add(giftToggleAction);

    }

    @Override
    public void onActionClicked(Action action) {
        if (action instanceof DanmakuPlayToggleAction) {
            dispatchActionCallback(action);
            ((DanmakuPlayToggleAction) action).nextIndex();
            notifyItemChanged((ArrayObjectAdapter) getControlsRow().getPrimaryActionsAdapter(),
                    action);
        } else if(action instanceof ChangePlayUrlAction) {
            dispatchActionCallback(action);
        }else if(action instanceof SuperChatToggleAction) {
            dispatchActionCallback(action);
            ((SuperChatToggleAction) action).nextIndex();
            notifyItemChanged((ArrayObjectAdapter) getControlsRow().getPrimaryActionsAdapter(),
                    action);
        } else if(action instanceof GiftToggleAction)  {
            dispatchActionCallback(action);
            ((GiftToggleAction) action).nextIndex();
            notifyItemChanged((ArrayObjectAdapter) getControlsRow().getPrimaryActionsAdapter(),
                    action);
        } else {
            super.onActionClicked(action);
        }
    }

    private void dispatchActionCallback(Action action){
        List<PlayerCallback> callbacks = getPlayerCallbacks();
        if (callbacks != null) {
            for (int i = 0, size = callbacks.size(); i < size; i++) {
                if(callbacks.get(i) instanceof LiveRoomPlayerCallback){
                    LiveRoomPlayerCallback callback = (LiveRoomPlayerCallback) callbacks.get(i);
                    if (action instanceof DanmakuPlayToggleAction) {
                        callback.onDanmakuToggle(((DanmakuPlayToggleAction) action).getIndex() == DanmakuPlayToggleAction.INDEX_ON);
                    } else if(action instanceof ChangePlayUrlAction){
                        callback.onLiveUrlChange(this);
                    } else if(action instanceof SuperChatToggleAction){
                        callback.onSuperChatToggle(((SuperChatToggleAction) action).getIndex() == SuperChatToggleAction.INDEX_ON);
                    } else if(action instanceof GiftToggleAction){
                        callback.onGiftToggle(((GiftToggleAction) action).getIndex() == GiftToggleAction.INDEX_ON);
                    }
                }
            }
        }
    }


    static class DanmakuPlayToggleAction extends PlaybackControlsRow.MultiAction {

        public static final int INDEX_ON = 0;
        public static final int INDEX_OFF = 1;

        public DanmakuPlayToggleAction(Context context) {
            super(R.id.playback_controls_danmaku_play_toggle);
            Drawable[] drawables = new Drawable[2];
            drawables[INDEX_ON] = getWhiteDrawable(context, R.drawable.ic_danmaku_enable);
            drawables[INDEX_OFF] = getWhiteDrawable(context, R.drawable.ic_danmaku_disable);

            setDrawables(drawables);
            String[] labels = new String[drawables.length];
            labels[INDEX_ON] = context.getString(R.string.playback_controls_danmu_play);
            labels[INDEX_OFF] = context.getString(R.string.playback_controls_danmu_stop);
            setLabels(labels);
        }
    }

    static class ChangePlayUrlAction extends Action {

        public ChangePlayUrlAction(Context context) {
            super(R.id.playback_controls_change_play_url);
            setIcon(getWhiteDrawable(context, R.drawable.ic_baseline_swap_horizontal_circle));
            setLabel1(context.getString(R.string.playback_controls_change_play_url));
        }
    }

    static class SuperChatToggleAction extends PlaybackControlsRow.MultiAction {

        public static final int INDEX_ON = 0;
        public static final int INDEX_OFF = 1;

        public SuperChatToggleAction(Context context) {
            super(R.id.playback_controls_super_chat_toggle);
            Drawable[] drawables = new Drawable[2];
            drawables[INDEX_ON] = getWhiteDrawable(context, R.drawable.ic_sc_enable);
            drawables[INDEX_OFF] = getWhiteDrawable(context, R.drawable.ic_sc_disable);

            setDrawables(drawables);
            String[] labels = new String[drawables.length];
            labels[INDEX_ON] = context.getString(R.string.playback_controls_sc_play);
            labels[INDEX_OFF] = context.getString(R.string.playback_controls_sc_stop);
            setLabels(labels);
        }
    }

    static class GiftToggleAction extends PlaybackControlsRow.MultiAction {

        public static final int INDEX_ON = 0;
        public static final int INDEX_OFF = 1;

        public GiftToggleAction(Context context) {
            super(R.id.playback_controls_super_chat_toggle);
            Drawable[] drawables = new Drawable[2];
            drawables[INDEX_ON] = getWhiteDrawable(context, R.drawable.ic_gift_enable);
            drawables[INDEX_OFF] = getWhiteDrawable(context, R.drawable.ic_gift_disable);

            setDrawables(drawables);
            String[] labels = new String[drawables.length];
            labels[INDEX_ON] = context.getString(R.string.playback_controls_gift_play);
            labels[INDEX_OFF] = context.getString(R.string.playback_controls_gift_stop);
            setLabels(labels);
        }
    }

    static Drawable getWhiteDrawable(Context context, int drawableId) {
        Drawable drawable = context.getDrawable(drawableId);
        Drawable whiteDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(whiteDrawable, Color.WHITE);
        return whiteDrawable;
    }

    public abstract static class LiveRoomPlayerCallback extends PlayerCallback {
        public void onDanmakuToggle(boolean enable) {}
        public void onLiveUrlChange(PlaybackGlue glue) {}
        public void onSuperChatToggle(boolean enable) {}
        public void onGiftToggle(boolean enable) {}
    }

}
