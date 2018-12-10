package be.tomjo.advent.day8;

import lombok.Value;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

@Value
public class LicenseFileNode {
    private final Header header;
    private final List<LicenseFileNode> childNodes;
    private final List<Integer> metadata;

    public LicenseFileNode(Header header, List<LicenseFileNode> childNodes, List<Integer> metadata) {
        checkArgument(metadata.size() >= 1);
        this.header = header;
        this.childNodes = childNodes;
        this.metadata = metadata;
    }

    public int getMetadataSum(){
        return metadata.stream()
                .mapToInt(i -> i)
                .sum()
                + childNodes.stream()
                .mapToInt(LicenseFileNode::getMetadataSum)
                .sum();
    }

    public int getValue(){
        if(childNodes.isEmpty()){
            return getMetadataSum();
        }
        return metadata.stream()
                .filter(this::isValidChildNodeReference)
                .map(this::toZeroBased)
                .map(childNodes::get)
                .mapToInt(LicenseFileNode::getValue)
                .sum();
    }

    private int toZeroBased(int i) {
        return i-1;
    }

    private boolean isValidChildNodeReference(Integer metadataEntry) {
        return metadataEntry > 0 && metadataEntry <= childNodes.size();
    }
}
