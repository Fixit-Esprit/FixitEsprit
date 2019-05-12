/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilis;

import java.util.Map;

/**
 *
 * @author hphqlim
 */
public class Utilis {

    public static int getKeys(Map<Integer, String> map, Object newValue) {
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (entry.getValue().equals(newValue)) {
                return entry.getKey();
            }
        }
        return -1;
    }
}
