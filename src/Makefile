# Your book files
DEPENDENCIES= toc preface about

# Placement of your HTML files
DOCS=docs/chapters

all: web

web: setup $(DEPENDENCIES)
        @cp $(DOCS)/toc/index.html $(DOCS)


# Creation and copy of stylesheet and images into
# the assets folder. This is important to deploy the
# website to Github Pages.
setup:
        @mkdir -p $(DOCS)
        @cp -r assets $(DOCS)


# Creation of folder and index.html file on a
# per-chapter basis

$(DEPENDENCIES):
        @mkdir -p $(DOCS)/$@
        @pandoc -s --toc web-metadata.yaml parts/$@.md \
        -c /assets/pandoc.css -o $(DOCS)/$@/index.html

clean:
        @rm -rf $(DOCS)

.PHONY: all clean web setup