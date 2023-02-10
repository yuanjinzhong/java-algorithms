package heap;

import java.lang.Integer;

public class MaxHeap extends Heap<Integer> {


    /**
     *  父元素和当前插入元素比较
     *  <p>大于0，表示父节点大于当前插入节点</p>
     *  <p>小于0，表示父节点小于当前插入节点</p>
     *
     *  <p></p>
     *  <p></p>
     *  父类模板方法中真正调用该方法的时候，用的{@code if(result>0)}的判断
     *  <p></p>
     *
     *  <p>在构造{@link 大顶堆 }的时候，父节点元素大于当前插入的元素，则当前元素的位置不需要动</p>
     *  <p></p>
     *
     * @param currentElement
     * @param parentElement
     * @return
     */
    @Override
    public int compareTo(Integer currentElement, Integer parentElement) {
        return parentElement.compareTo(currentElement);
    }

    public static void main(String[] args) {
        MaxHeap maxHeap = new MaxHeap();
        maxHeap.add(4);
        maxHeap.add(3);
        maxHeap.add(2);
        maxHeap.add(1);
    }


}