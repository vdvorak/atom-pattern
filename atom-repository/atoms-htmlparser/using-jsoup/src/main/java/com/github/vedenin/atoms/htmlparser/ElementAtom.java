package com.github.vedenin.atoms.htmlparser;

import com.github.vedenin.atom.annotations.Atom;
import com.github.vedenin.atom.annotations.BoilerPlate;
import com.github.vedenin.atom.annotations.Molecule;
import com.github.vedenin.atoms.collections.ListAtom;
import com.github.vedenin.atoms.collections.SetAtom;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;

/**
 * This Atom pattern (pattern that extends a Proxy/Facade pattern that have only minimal methods from original class)
 * This using to light-reference to another third party open-source libraries
 *
 * Created by Slava Vedenin on 12/16/2016.
 */
@Atom(Element.class)
@Molecule({ElementAtom.class, TagAtom.class, DocumentAtom.class, ListAtom.class})
@SuppressWarnings({"unused", "WeakerAccess"})
public class ElementAtom {
    private final Element original;

    public String getText() {
        return original.text();
    }

    public TagAtom getTag() {
        return TagAtom.getAtom(original.tag());
    }

    public SetAtom<String> getClassNames() {
        return SetAtom.getAtom(original.classNames());
    }

    public String getAttr(String name) {
        return original.attr(name);
    }

    public SetAtom<String> getAttributes() {
        SetAtom<String> result = SetAtom.create();
        for(Attribute attribute: original.attributes()) {
            result.add(attribute.getKey());
        }
        return result;
    }

    /**
     * Return sets : tag [attribute]
     * @return sets : tag [attribute]
     */
    public SetAtom<String> getTagAndAttributes() {
        SetAtom<String> result = SetAtom.create();
        for(Attribute attribute: original.attributes()) {
            result.add(original.tag() + "[" + attribute.getKey() + "]");
        }
        return result;
    }

    public SetAtom<String> getTagAttributeAndValues() {
        SetAtom<String> result = SetAtom.create();
        for(Attribute attribute: original.attributes()) {
            result.add(original.tag() + "[" + attribute.getKey() + "="+ attribute.getValue() + "]");
        }
        return result;
    }

    public String getId() {
        return original.id();
    }

    public String getOwnText() {
        return original.ownText();
    }

    public ListAtom<ElementAtom> getChild() {
        return original.children().stream().map(ElementAtom::getAtom).collect(ListAtom.getCollector());
    }

    public ListAtom<ElementAtom> getAllParent() {
        return original.parents().stream().map(ElementAtom::getAtom).collect(ListAtom.getCollector());
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ElementAtom && original.equals(((ElementAtom) o).original);
    }

    @Override
    public int hashCode() {
        return original != null ? original.hashCode() : 0;
    }

    // Just boilerplate code for Atom
    @BoilerPlate
    private ElementAtom(Element original) {
        this.original = original;
    }

    @BoilerPlate
    static ElementAtom getAtom(Element element) {
        return new ElementAtom(element);
    }

}
