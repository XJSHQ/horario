/*
 * Copyright (C) 2017 Artem Chepurnoy <artemchep@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
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
package com.artemchep.basic.utils;

import android.support.annotation.NonNull;
import android.util.Patterns;

/**
 * @author Artem Chepurnoy
 */
public class PatternUtils {

    public static boolean isEmail(@NonNull String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isPhone(@NonNull String phone) {
        return Patterns.PHONE.matcher(phone).matches();
    }

}
