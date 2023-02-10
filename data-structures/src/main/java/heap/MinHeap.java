package heap;

import java.lang.Integer;

public class MinHeap extends Heap<Integer> {

    /**
     * 当前元素和父节点比较
     * <p>大于0，则表示当前节点大于父节点</p>
     * <p>小于0，表示当前节点小于父节点</p>
     * <p></p>
     * <p></p>
     * 父类模板方法中真正调用该方法的时候，用的{@code if(result>0)}的判断
     * <p></p>
     *
     * <p>在构造{@linkplain 小顶堆 }的时候，表示插入元素大于父节点，则当前元素的位置不需要动</p>
     * <p></p>
     * @param currentElement
     * @param parentElement
     * @return
     */
    @Override
    public int compareTo(Integer currentElement, Integer parentElement) {
        return currentElement.compareTo(parentElement);
    }


    public static void main(String[] args) {

        MinHeap minHeap = new MinHeap();
        minHeap.add(4);
        minHeap.add(9);
        minHeap.add(3);
        minHeap.add(2);
        minHeap.add(6);


    }

}
