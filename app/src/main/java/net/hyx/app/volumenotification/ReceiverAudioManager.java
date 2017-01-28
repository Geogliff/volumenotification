/*
 * Copyright (C) 2017 Seht (R) HyX
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.hyx.app.volumenotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

public class ReceiverAudioManager extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        int stream_type = intent.getExtras().getInt("stream_type");
        int direction = intent.getExtras().getInt("direction");

        AudioManager audio_manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        audio_manager.adjustStreamVolume(stream_type, direction, AudioManager.FLAG_SHOW_UI);
    }

}