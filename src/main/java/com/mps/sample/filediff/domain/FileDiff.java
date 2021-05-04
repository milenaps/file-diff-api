package com.mps.sample.filediff.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "file_diffs")
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class FileDiff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean equal;
    private boolean sameSize;
    private long filesLength;

    @Column(name = "diff_offset")
    private long offset;

    public boolean isEqual() {
        return equal;
    }

    public void setEqual(boolean equal) {
        this.equal = equal;
    }

    public Boolean isSameSize() {
        return sameSize;
    }

    public void setSameSize(boolean sameSize) {
        this.sameSize = sameSize;
    }

    public Long getFilesLength() {
        return filesLength;
    }

    public void setFilesLength(long filesLength) {
        this.filesLength = filesLength;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileDiff fileDiff = (FileDiff) o;
        return Objects.equals(equal, fileDiff.equal) && Objects.equals(sameSize, fileDiff.sameSize) && Objects.equals(offset, fileDiff.offset) && Objects.equals(filesLength, fileDiff.filesLength);
    }

    @Override
    public int hashCode() {
        return Objects.hash(equal, sameSize, offset, filesLength);
    }

    @Override
    public String toString() {
        return "FileDiff{" +
                "equal=" + equal +
                ", sameSize=" + sameSize +
                ", offset=" + offset +
                ", filesLength=" + filesLength +
                '}';
    }
}
