package com.mps.sample.filediff.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "file_diffs")
public class FileDiff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean equal;
    private boolean sameSize;
    private long diffOffset;
    private long diffLength;

    public boolean isEqual() {
        return equal;
    }

    public void setEqual(boolean equal) {
        this.equal = equal;
    }

    public boolean isSameSize() {
        return sameSize;
    }

    public void setSameSize(boolean sameSize) {
        this.sameSize = sameSize;
    }

    public long getDiffOffset() {
        return diffOffset;
    }

    public void setDiffOffset(long diffOffset) {
        this.diffOffset = diffOffset;
    }

    public long getDiffLength() {
        return diffLength;
    }

    public void setDiffLength(long diffLength) {
        this.diffLength = diffLength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileDiff fileDiff = (FileDiff) o;
        return equal == fileDiff.equal && sameSize == fileDiff.sameSize && diffOffset == fileDiff.diffOffset && diffLength == fileDiff.diffLength;
    }

    @Override
    public int hashCode() {
        return Objects.hash(equal, sameSize, diffOffset, diffLength);
    }

    @Override
    public String toString() {
        return "FileDiff{" +
                "equal=" + equal +
                ", sameSize=" + sameSize +
                ", diffOffset=" + diffOffset +
                ", diffLength=" + diffLength +
                '}';
    }
}
