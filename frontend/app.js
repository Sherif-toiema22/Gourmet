let cart = [];

/* ── CART ── */
function addToCart(name, emoji, price, btn) {
  const existing = cart.find(i => i.name === name);
  if (existing) {
    existing.qty++;
  } else {
    cart.push({ name, emoji, price, qty: 1 });
  }
  updateCart();
  showToast(`✅ تمت إضافة ${name}`);

  btn.textContent = '✓';
  btn.style.background = '#2ECC71';
  setTimeout(() => {
    btn.textContent = '+';
    btn.style.background = 'var(--orange)';
  }, 800);
}

function updateCart() {
  const count = cart.reduce((s, i) => s + i.qty, 0);
  document.getElementById('cartCount').textContent = count;

  const itemsEl  = document.getElementById('cartItems');
  const footerEl = document.getElementById('cartFooter');

  if (cart.length === 0) {
    itemsEl.innerHTML = '<p style="color:var(--muted);text-align:center;padding:40px 0;">سلتك فارغة! أضف بعض الأطباق 🍽️</p>';
    footerEl.style.display = 'none';
    return;
  }

  footerEl.style.display = 'block';
  itemsEl.innerHTML = cart.map((item, idx) => `
    <div class="cart-item">
      <div class="cart-item-emoji">${item.emoji}</div>
      <div class="cart-item-info">
        <div class="cart-item-name">${item.name}</div>
        <div class="cart-item-price">${(item.price * item.qty).toFixed(3)} ر.ع</div>
        <div class="qty-control">
          <button class="qty-btn" onclick="changeQty(${idx}, -1)">−</button>
          <span class="qty">${item.qty}</span>
          <button class="qty-btn" onclick="changeQty(${idx}, 1)">+</button>
        </div>
      </div>
    </div>
  `).join('');

  const total = cart.reduce((s, i) => s + i.price * i.qty, 0);
  document.getElementById('cartTotal').textContent = total.toFixed(3) + ' ر.ع';
}

function changeQty(idx, delta) {
  cart[idx].qty += delta;
  if (cart[idx].qty <= 0) cart.splice(idx, 1);
  updateCart();
}

function toggleCart() {
  document.getElementById('cartOverlay').classList.toggle('open');
}

function closeCartOutside(e) {
  if (e.target === document.getElementById('cartOverlay')) toggleCart();
}

/* ── TOAST ── */
function showToast(msg) {
  const t = document.getElementById('toast');
  t.textContent = msg;
  t.classList.add('show');
  setTimeout(() => t.classList.remove('show'), 2000);
}

/* ── CATEGORY FILTER ── */
function filterCategory(cat, el) {
  document.querySelectorAll('.cat-item').forEach(c => c.classList.remove('active'));
  el.classList.add('active');
  document.querySelectorAll('#foodGrid .food-card').forEach(card => {
    card.style.display = (cat === 'all' || card.dataset.category === cat) ? '' : 'none';
  });
}

/* ── RESTAURANT FILTER BUTTONS ── */
document.addEventListener('DOMContentLoaded', () => {
  document.querySelectorAll('.filter-btn').forEach(btn => {
    btn.addEventListener('click', function () {
      document.querySelectorAll('.filter-btn').forEach(b => b.classList.remove('active'));
      this.classList.add('active');
    });
  });
});
