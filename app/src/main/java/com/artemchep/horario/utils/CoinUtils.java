/*
 * Copyright (C) 2017 XJSHQ@github.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package com.artemchep.horario.utils;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.artemchep.horario.interfaces.ICoin;

/**
 * @author Artem Chepurnoy
 */
public class CoinUtils {

    @NonNull
    public static Intent getPaymentIntent(@NonNull ICoin coin) {
        return getPaymentIntent(coin, coin.getPaymentAmount());
    }

    @NonNull
    public static Intent getPaymentIntent(@NonNull ICoin coin, double amount) {
        Uri uri = coin.getPaymentUri(amount);
        return new Intent(Intent.ACTION_VIEW, uri);
    }

}
