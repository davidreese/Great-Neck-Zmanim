function showAddAccountModal() {
    console.log("add-account-btn clicked");
    document.getElementById("add-account-btn").click();
}

function replaceSaturdayWithShabbat() {
    // Get the root node of the document
    const root = document.documentElement;
    
    // Create a tree walker to get all text nodes
    const treeWalker = document.createTreeWalker(root, NodeFilter.SHOW_TEXT, null, false);
    
    // Loop through all text nodes and replace the text
    let node;
    while (node = treeWalker.nextNode()) {
      const text = node.textContent;
      
      // Replace 'Saturday' with 'Shabbat' in the text content
      const replacedText = text.replace(/\bSaturday\b/g, 'Shabbat');
      
      // If the text content was modified, update the node
      if (text !== replacedText) {
        node.textContent = replacedText;
      }
    }
}
    
replaceSaturdayWithShabbat();