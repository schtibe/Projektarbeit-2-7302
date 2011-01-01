.PHONY: default pdf dvi clean distclean
VIEWER := okular
SHELL := /bin/bash
MASTER_SRC := $(wildcard *.tex)
INCLUDE_SRC := $(wildcard includes/*.tex)
TARGET_PDF := $(addsuffix .pdf, $(basename $(MASTER_SRC)))
TARGET_DVI := $(addsuffix .dvi, $(basename $(MASTER_SRC)))
CLEAN_EXT := aux,log,idx,out,glo,toc,lot,tex~,backup

# default target builds pdf
default: pdf
pdf: $(TARGET_PDF)
dvi: $(TARGET_DVI)

# build pdf from master source using pdflatex
%.pdf: %.tex $(INCLUDE_SRC)
	# do it twice for the indexes
	pdflatex -interaction=nonstopmode "$<" >/dev/null
	pdflatex -interaction=nonstopmode "$<" >/dev/null
	if [[ -a $@ ]]; then $(VIEWER) $@; fi

# build dvi from master source using latex
%.dvi: %.tex $(INCLUDE_SRC)
	# do it twice for the indexes
	latex -interaction=nonstopmode "$<" >/dev/null
	latex -interaction=nonstopmode "$<" >/dev/null
	if [[ -a $@ ]]; then $(VIEWER) $@; fi

# cleans index, logs, temporary files etc. 
clean:
	rm -f *.{$(CLEAN_EXT)} includes/*.{$(CLEAN_EXT)}

# also removes output documents
distclean: clean
	rm -f *.{pdf,dvi}