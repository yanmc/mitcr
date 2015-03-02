/*
 * MiTCR <http://milaboratory.com>
 *
 * Copyright (c) 2010-2013:
 *     Bolotin Dmitriy     <bolotin.dmitriy@gmail.com>
 *     Chudakov Dmitriy    <chudakovdm@mail.ru>
 *
 * MiTCR is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.milaboratory.mitcr.vdjmapping.ntree;

import com.milaboratory.mitcr.util.evolver.AbstractGenerator;

import java.util.List;

/**
 * @author Bolotin Dmitriy (bolotin.dmitriy@gmail.com)
 */
public class NTreeNodeGeneratorBadMismatch3B extends AbstractGenerator<NTreeSlider, NucleotideInfo> implements NTreeNodeGenerator {
    public static final int MAX_MISMATCHES = 3;
    public static final NTreeNodeGeneratorBadMismatch3B INSTANCE = new NTreeNodeGeneratorBadMismatch3B();

    @Override
    protected void addToNext(NTreeSlider current, List<NTreeSlider> nextGeneration, NucleotideInfo condition) {
        if (condition.bad) {
            for (byte i = 0; i < 4; ++i)
                if (current.canSlide(i) && (i == condition.code || current.badSlides < MAX_MISMATCHES))
                    nextGeneration.add(current.copy().slide(i).incrementSlide(i != condition.code));
        } else if (current.canSlide(condition.code))
            nextGeneration.add(current.slide(condition.code).incrementGoodSlide());
    }
}
